import mongoose = require("mongoose");

import { IFlavor } from './flavor';

interface IOrder {
    items: Array<IOrderItem>;
}

interface IOrderItem {
    containerType: string;
    
    /** string should be changed for a flavor scheme */
    flavors: Array<string>;
    sauce: string;
    addins: Array<string>;
    quantity: number;
    delivered: boolean;

}

interface IOrderModel extends IOrder, mongoose.Document { }

interface IOrderItemModel extends IOrderItem, mongoose.Document { }

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

var Order = mongoose.model<IOrderModel>("Order", OrderSchema);

export = Order;