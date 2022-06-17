package com.data.factory.models

import com.data.factory.exceptions.ControllerException

import scala.util.matching.Regex

class DataConverter {

  val validField: String => Regex => Boolean = field => matcher =>
    field match {
      case matcher(_*) => true
      case _ => false
    }

  val validStringField: String => String => Boolean = fieldName => fieldValue =>
    if (fieldValue == null || fieldValue.isEmpty) throw ControllerException("%s cannot be null or empty.".format(fieldName))
    else !(fieldValue == null || fieldValue.isEmpty)

  val stringEndsWithSlash: Regex = "([a-zA-Z0-9\\-\\/]+)\\/$".r
  val validPath: String => String = field => field match {
      case stringEndsWithSlash(path) => path
      case _ => field
    }

  val generateObjectStoragePath: String => String => String => String =
    cloudProvider => bucketName => objectPath => {
      validStringField("cloudProvider")(cloudProvider)
      validStringField("bucketName")(bucketName)
      validStringField("objectPath")(objectPath)
      "%s://%s/%s".format(validPath(cloudProvider), validPath(bucketName), validPath(objectPath))
    }
}