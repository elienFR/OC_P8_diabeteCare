package com.mediscreen.microservicereport.model;

public enum Gender {
  F("female"),
  M("male"),
  NB("non-binary"),
  NA("not communicated");

  final String name;

  Gender(String name){
    this.name =name;
  }

  @Override
  public String toString() {
    return "Gender{" +
      "gender='" + this.name + '\'' +
      '}';
  }
}
