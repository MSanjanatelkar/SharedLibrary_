package org.k9.scm

class Git implements Serializable {
  def config
  def script

  Git(script,config) {
    this.config = config
    this.script = script
  }

  void gitClone(def parameter = false) {
      if (this.script.env.ghprbSourceBranch && this.script.env.ghprbTargetBranch) {
            return this.pullRequestBuilder(stage: "Git Pull Request Builder")
        } else {
            return this.checkout(stage: "Git Initialize")
        }
    }
}    
