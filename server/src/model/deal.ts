import mongoose = require('mongoose');

export interface IDeal {
    title: string;
    description: string;
    isLastDeal: boolean
}

export var DealSchema = new mongoose.Schema({
    title: String,
    description: String,
    isLastDeal: Boolean
});

export interface IDealModel extends IDeal, mongoose.Document {
}

export var Deal = mongoose.model<IDealModel>('Deal', DealSchema);
