package com.data.factory.models

import com.data.factory.exceptions.ControllerException
import org.scalatest._

import scala.util.matching.Regex

class TestDataConverter extends FlatSpec{

  "validField" should "return true when receive a valid string from alpha numeric regex." in {

    val request: DataConverter = new DataConverter

    val test = "alphaNumeric19"
    val regex: Regex = "[0-9a-zA-Z]+".r

    val actual = request.validField(test)(regex)
    assert(actual)
  }

  it should "return false when receive a invalid string from alpha numeric regex." in {

    val request: DataConverter = new DataConverter

    val test = "invalid$"
    val regex: Regex = "[0-9a-zA-Z]+".r

    val actual = request.validField(test)(regex)
    assert(!actual)
  }

  "validStringField" should "return true when string contains a valid value." in {
    val request: DataConverter = new DataConverter
    val validString = "this string contains some value."
    val actual = request.validStringField("validString")(validString)
    assert(actual)
  }

  it should "return false when string is null." in {
    val request: DataConverter = new DataConverter
    val someString = null
    val actual =
      intercept[ControllerException]{
        request.validStringField("invalidString")(someString)
      }
    val expected = "invalidString cannot be null or empty."
    assert(expected == actual.getMessage)
  }

  it should "return false when string is empty." in {
    val request: DataConverter = new DataConverter
    val someString = ""
    val actual =
      intercept[ControllerException]{
        request.validStringField("invalidString")(someString)
      }
    val expected = "invalidString cannot be null or empty."
    assert(expected == actual.getMessage)
  }

  "validPath" should "return a string without slash." in {
    val request = new DataConverter
    val some = "my-path/ends/with/slash"
    val test = "%s/".format(some)
    val actual = request.validPath(test)
    assert(actual == some)
  }

  it should "return same string." in {
    val request = new DataConverter
    val some = "my-path/ends/with/slash"
    val actual = request.validPath(some)
    assert(actual == some)
  }

  "generateObjectStoragePath" should "return a valid object storage path." in {
    val request = new DataConverter
    val cloudProvider = "s3"
    val bucket = "my-bucket"
    val prefix = "folder/data/file.parquet"
    val expected = "%s://%s/%s".format(cloudProvider, bucket, prefix)
    val actual = request.generateObjectStoragePath(cloudProvider)(bucket)(prefix)
    assert(expected == actual)
  }
}
