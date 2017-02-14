"use strict";
const mongoose = require("mongoose");
exports.DealSchema = new mongoose.Schema({
    title: String,
    description: String,
    isLastDeal: Boolean
});
exports.Deal = mongoose.model("Deal", exports.DealSchema);
