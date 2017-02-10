"use strict";
const mongoose = require("mongoose");
var AddinSchema = new mongoose.Schema({
    label: String
});
var Addin = mongoose.model("Addin", AddinSchema);
module.exports = Addin;
