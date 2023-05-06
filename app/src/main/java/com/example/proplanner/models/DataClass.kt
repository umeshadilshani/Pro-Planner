//IT21313684
//Karunarathna D.T.S
package com.example.proplanner.models

class DataClass {
    // Properties of the project plan.
    var dataProName: String? = null
    var dataProDesc: String? = null
    var dataTPeriod : String? = null
    var dataTask: String? = null

    // Constructor that initializes the properties of the project plan.
    constructor(dataProName:String?, dataProDesc:String?,  dataTPeriod:String?, dataTask:String?){
        this.dataProName = dataProName
        this.dataProDesc = dataProDesc
        this.dataTPeriod = dataTPeriod
        this.dataTask = dataTask

    }

    constructor(){

    }
}