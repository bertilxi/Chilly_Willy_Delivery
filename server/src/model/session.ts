import mongoose = require("mongoose");
import order = require('./order');

interface IOrder {
    items: Array<IOrderItem>;
}

interface IOrderItem {
    containerType: string;
    containerSize: string;
    flavors: Array<string>;
    sauce: string;
    addins: Array<string>;
    quantity: number;
    delivered: boolean;

}

interface ISession {
    orders: Array<IOrder>
}

var OrderItemSchema = new mongoose.Schema({
    containerType: String,
    containerSize: String,
    flavors: [String],
    sauce: String,
    addins: [String],
    quantity: Number,
    delivered: Boolean
});

var OrderSchema = new mongoose.Schema({
    items: [OrderItemSchema]
});

var SessionSchema = new mongoose.Schema({
    orders: [OrderSchema]
});

interface ISessionModel extends ISession, mongoose.Document { }

var Session = mongoose.model<ISessionModel>("Session", SessionSchema);

export = Session;