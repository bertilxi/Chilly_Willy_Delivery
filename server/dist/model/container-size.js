"use strict";
const mongoose = require("mongoose");
var ContainerSizeSchema = new mongoose.Schema({
    label: String
});
var ContainerSize = mongoose.model("ContainerSize", ContainerSizeSchema);
module.exports = ContainerSize;
