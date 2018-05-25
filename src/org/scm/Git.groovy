package org.scm

class Git implements Serializable {
  def config
  def script

  Git(script,config) {
    this.config = config
    this.script = script
  }

  void gitClone(def parameter = false) {
     
    this.script.sh "successful shared library"
}    
