"use strict";
const mongoose = require("mongoose");
var ContainerTypeSchema = new mongoose.Schema({
    label: String
});
var ContainerType = mongoose.model("ContainerType", ContainerTypeSchema);
module.exports = ContainerType;
