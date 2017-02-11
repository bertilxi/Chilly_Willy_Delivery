import mongoose = require("mongoose");


export interface IReview {
    rating: number;
    img: string; // base64 image
    comment: string;
}

export var ReviewSchema = new mongoose.Schema({
    rating: Number,
    img: String, // base64 image
    comment: String
});

export interface IReviewModel extends IReview, mongoose.Document { }

export var Review = mongoose.model<IReviewModel>("Review", ReviewSchema);


