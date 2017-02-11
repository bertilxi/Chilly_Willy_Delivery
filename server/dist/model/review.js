"use strict";
const mongoose = require("mongoose");
exports.ReviewSchema = new mongoose.Schema({
    rating: Number,
    img: String,
    comment: String
});
exports.Review = mongoose.model("Review", exports.ReviewSchema);
