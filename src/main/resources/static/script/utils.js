const utils = {
  setPriceFormat: function (value) {
    value = String(value);
    return value.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
  },

  unsetPriceFormat: function (value) {
    value = String(value);
    return value.replace(/\D+/g, '');
  },
}