"use strict";
const mongoose = require("mongoose");
exports.SauceSchema = new mongoose.Schema({
    label: String,
    imgURL: String
});
exports.Sauce = mongoose.model("Sauce", exports.SauceSchema);
