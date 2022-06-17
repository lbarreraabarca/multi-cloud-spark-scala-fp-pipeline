package com.data.factory

import com.data.factory.exceptions.ControllerException
import org.json4s._
import org.json4s.native.Serialization
import org.json4s.native.Serialization.read
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.typesafe.scalalogging.Logger

object App extends Serializable{
  val log = Logger("App")
    def main(args: Array[String]): Unit = {
        val encodedInput: String = args(0)
        try{
          log.info("Creating sparkSession.")
      }
      catch{
          case e:Exception => throw ControllerException(e.getMessage)
      }
    }
}
