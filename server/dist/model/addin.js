"use strict";
const mongoose = require("mongoose");
exports.AddinSchema = new mongoose.Schema({
    label: String,
    imgURL: String
});
exports.Addin = mongoose.model("Addin", exports.AddinSchema);
