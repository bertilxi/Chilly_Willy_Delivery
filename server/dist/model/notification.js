"use strict";
const mongoose = require("mongoose");
exports.DealSchema = new mongoose.Schema({
    title: String,
    description: String
});
exports.NotificationSchema = new mongoose.Schema({
    deals: [exports.DealSchema]
});
exports.Notification = mongoose.model("Notification", exports.NotificationSchema);
