"use strict";
const mongoose = require("mongoose");
exports.ContainerTypeSchema = new mongoose.Schema({
    label: String,
    maxFlavors: Number,
    variableQuantityOfFlavors: Boolean,
    priceInCents: Number
});
exports.ContainerType = mongoose.model("ContainerType", exports.ContainerTypeSchema);
