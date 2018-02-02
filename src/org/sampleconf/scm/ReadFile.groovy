package org.k9.utilities

class ReadFile implements Serializable {
  def config
  def script

  ReadFile(script,config) {
    this.config = config
    this.script = script
  }

  void read() {
  }
}
