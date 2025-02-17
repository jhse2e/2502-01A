document.addEventListener("DOMContentLoaded", function (e) {
  page.setOrder().then();
  page.setOrderPayment().then((paymentStatus) => page.setOrderPaymentCancelButton(paymentStatus));

  document.querySelector(".order-payment-cancel-action .button")?.addEventListener("click", function (event) {
    page.cancelOrderPayment().then();
  });
});

const page = {

  cancelOrderPayment: async function () {
    const orderId = document.querySelector(".order .order-info .order-id .text").innerHTML;
    const transactionReason = document.querySelector(".order-payment-cancel .select").options[document.querySelector(".order-payment-cancel .select").selectedIndex].value;
    const { code, message, result } = await app.usePostByPaymentCancel(orderId, transactionReason);
  },

  /**
   *
   */
  setOrder: async function () {
    const { code, message, result } = await app.useGetByOrder(location.pathname.split("/")[2]);

    const order = result;
    document.querySelector(".order .order-info .order-id .text").innerHTML = order.id;
    document.querySelector(".order .order-info .order-amount .text").innerHTML = `${utils.setPriceFormat(order.amount)}원`;
    document.querySelector(".order .order-info .order-status .text").innerHTML = order.status;
  },

  /**
   *
   */
  setOrderPayment: async function () {
    const { code, message, result } = await app.useGetByPayment(location.pathname.split("/")[2]);

    const payment = result;
    document.querySelector(".order-payment .order-payment-info .order-payment-id .text").innerHTML = payment.id;
    document.querySelector(".order-payment .order-payment-info .order-payment-amount .text").innerHTML = `${utils.setPriceFormat(payment.amount)}원`;
    document.querySelector(".order-payment .order-payment-info .order-payment-status .text").innerHTML = payment.status;

    const selectElement = document.querySelector(".order-payment-cancel .select");
    for (let i = 0; i < selectElement.options.length; i++) {
      if (selectElement.options[i].value == payment.reason) {
        selectElement.options[i].selected = true;
      }
    }

    return { paymentStatus: payment.status };
  },

  setOrderPaymentCancelButton: function ({ paymentStatus }) {
    if (paymentStatus !== "결제 완료") {
      document.querySelector(".order-payment-cancel .select").disabled = true;
      document.querySelector(".order-payment-cancel-action .button").disabled = true;
    }
  },
};