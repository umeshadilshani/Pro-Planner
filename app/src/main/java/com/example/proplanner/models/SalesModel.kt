//IT21321504
//Gunawardana N.B.C.A.W.

package com.example.proplanner.models


 class SalesModel{
     // Properties of the sales
    var prName: String? = null
    var prBatchNo: String? = null
    var prQuan: String? = null
    var prUniPri: String? = null
    var prDate: String? = null


     // Constructor that initializes the properties of the sales.
    constructor(prName: String?, prBatchNo: String?, prQuan: String?, prUniPri: String?,prDate: String?) {
        this.prName = prName
        this.prBatchNo = prBatchNo
        this.prQuan = prQuan
        this.prUniPri = prUniPri
        this.prDate = prDate

    }
    constructor(){

    }


}