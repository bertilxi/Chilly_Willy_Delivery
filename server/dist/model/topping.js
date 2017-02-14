"use strict";
const mongoose = require("mongoose");
exports.ToppingSchema = new mongoose.Schema({
    label: String,
    imgURL: String
});
exports.Topping = mongoose.model("Topping", exports.ToppingSchema);
