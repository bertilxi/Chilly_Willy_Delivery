"use strict";
const mongoose = require("mongoose");
var ReviewSchema = new mongoose.Schema({
    rating: Number,
    imgUrl: String,
    comment: String,
});
var Review = mongoose.model("Notification", ReviewSchema);
module.exports = Review;
