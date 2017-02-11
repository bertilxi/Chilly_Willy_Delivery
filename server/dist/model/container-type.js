"use strict";
const mongoose = require("mongoose");
exports.ContainerTypeSchema = new mongoose.Schema({
    label: String,
    maxFlavors: Number,
    variableQuantityOfFlavors: Boolean
});
exports.ContainerType = mongoose.model("ContainerType", exports.ContainerTypeSchema);
