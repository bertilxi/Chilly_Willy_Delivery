"use strict";
const mongoose = require("mongoose");
var SauceSchema = new mongoose.Schema({
    label: String
});
var Sauce = mongoose.model("Sauce", SauceSchema);
module.exports = Sauce;
