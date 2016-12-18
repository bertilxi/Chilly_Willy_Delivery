
import mongoose = require("mongoose");

interface INotification {
    deals: Array<IDeal>;
}

interface IDeal {
    title: string;
    description: string;
}

var DealSchema = new mongoose.Schema({
    title: String,
    description: String
});

var NotificationSchema = new mongoose.Schema({
    deals: [DealSchema]
});

interface INotificationModel extends INotification, mongoose.Document { }

var Notification = mongoose.model<INotificationModel>("Notification", NotificationSchema);

export = Notification;