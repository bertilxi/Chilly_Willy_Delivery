"use strict";
const Flavor = require("./model/flavor");
const flavors_1 = require("./data/flavors");
class DbHelper {
    constructor() {
        Flavor.remove({}, error => {
            if (error) {
                console.log(error);
            }
        });
    }
    init() {
        console.log(flavors_1.flavors);
        for (let x of flavors_1.flavors) {
            new Flavor(x).save();
        }
    }
}
exports.DbHelper = DbHelper;
