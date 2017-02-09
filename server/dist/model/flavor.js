"use strict";
const mongoose = require("mongoose");
var FlavorSchema = new mongoose.Schema({
    label: String,
    img: String
});
var Flavor = mongoose.model("Flavor", FlavorSchema);
module.exports = Flavor;
