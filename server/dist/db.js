"use strict";
const flavor_1 = require("./model/flavor");
const container_type_1 = require("./model/container-type");
const addin_1 = require("./model/addin");
const sauce_1 = require("./model/sauce");
const deal_1 = require("./model/deal");
const order_1 = require("./model/order");
const flavors_1 = require("./data/flavors");
const container_types_1 = require("./data/container-types");
const sauce_2 = require("./data/sauce");
const addins_1 = require("./data/addins");
const deals_1 = require("./data/deals");
class DbHelper {
    constructor() {
        this.resetMetadata();
    }
    resetMetadata() {
        flavor_1.Flavor.remove({}, () => { });
        container_type_1.ContainerType.remove({}, () => { });
        sauce_1.Sauce.remove({}, () => { });
        addin_1.Addin.remove({}, () => { });
        order_1.Order.remove({}, () => { });
        deal_1.Deal.remove({}, () => { });
    }
    init() {
        flavors_1.flavors.forEach(x => new flavor_1.Flavor(x).save());
        container_types_1.containerTypes.forEach(x => new container_type_1.ContainerType(x).save());
        sauce_2.sauces.forEach(x => new sauce_1.Sauce(x).save());
        addins_1.addins.forEach(x => new addin_1.Addin(x).save());
        deals_1.deals.forEach(x => new deal_1.Deal(x).save());
    }
}
exports.DbHelper = DbHelper;
