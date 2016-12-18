"use strict";
const mongoose = require("mongoose");
class DbSchema {
    constructor() {
        let schema = new mongoose.Schema({});
        mongoose.model('db-chema', schema);
    }
}
