package org.sampleconf.scm


	class Deployment implements Serializable {
	  def config
	  def script


	  Deployment(script, config) {
	    this.config = config
	    this.script = script
	  }


void deployFromJson() {
	    
	    openshift.withCluster('https://192.168.99.102:8443','oIDbWZt0qPxaG2k9rkPjCQUIbt1t7tlhv1tSEMEoYyw') {
	    echo "Using project: ${openshift.project()}"
      	    def objs = openshift.create(config.templatePath)
             objs.describe()
	} 
    } 
}

