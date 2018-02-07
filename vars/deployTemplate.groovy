#!/usr/bin/groovy
def call(body) {
    // evaluate the body block, and collect configuration into the object
    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()

    def rc = """
    {
  "apiVersion": "v1",
  "kind": "List",
  "items": [
    {
      "apiVersion": "v1",
      "kind": "Service",
      "metadata": {
        "name": “{config.name}”
      },
      "spec": {
        "ports": [
          {
            "port": 8020,
            "targetPort": 8020
          }
        ],
        "selector": {
          "deploymentconfig": "{config.name}"
        }
      }
    },
    {
      "apiVersion": "v1",
      "kind": "DeploymentConfig",
      "metadata": {
        "name": "{config.name}"
      },
      "spec": {
        "replicas": 2,
        "selector": {
          "deploymentconfig": "{config.name}"
        },
        "strategy": {
          "type": "Recreate"
        },
        "template": {
          "metadata": {
            "labels": {
              "deploymentconfig": "{config.name}"
            }
          },
          "spec": {
            "containers": [
              {
                "image": "{config.imagename}",
                "name": "daac",
                "ports": [
                  {
                    "containerPort": 8020,
                    "protocol": "TCP"
                  }
                ]
              }
            ]
          }
        },
        "triggers": [
          {
            "type": "ConfigChange"
          },
          {
            "imageChangeParams": {
              "automatic": true,
              "containerNames": [
                "daac"
              ],
              "from": {
                "kind": "ImageStreamTag",
                "name": "daactemplate:latest"
              }
            },
            "type": "ImageChange"
          }
        ]
      }
    },
    {
      "apiVersion": "v1",
      "kind": "ImageStream",
      "metadata": {
        "name": "daactemplate"
      },
      "spec": {
        "dockerImageRepository": "{config.imagename}"
      }
    },
    {
      "apiVersion": "v1",
      "kind": "Route",
      "metadata": {
        "name": "template-route"
      },
      "spec": {
        "to": {
          "kind": "Service",
          "name": "template-service"
        }
      }
    }
  ]
}
    """

    echo 'using Kubernetes resources:\n' + rc
    return rc

  }
