package org.k9.utilities

import org.k9.*

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
