"use strict";
const mongoose = require("mongoose");
exports.FlavorSchema = new mongoose.Schema({
    label: String,
    imgURL: String
});
exports.Flavor = mongoose.model("Flavor", exports.FlavorSchema);
