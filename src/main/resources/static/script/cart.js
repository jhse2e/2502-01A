document.addEventListener("DOMContentLoaded", function (e) {
  page.setCartProductList().then();

  document.querySelector(".cart-product-order .select")?.addEventListener("change", function (event) {
    page.setCartProduct(document.querySelector(".cart-product-order .select").options[document.querySelector(".cart-product-order .select").selectedIndex].value).then(() => page.setCartAmount());
  });

  document.querySelector(".cart-product-item-list")?.addEventListener("change", function (event) {
    if (event.target.classList.contains("cart-product-quantity")) {
      const cartProductItemElement = event.target.closest(".cart-product-item");

      const cartProductPrice = Number(utils.unsetPriceFormat(cartProductItemElement.querySelector(".cart-product-price").innerHTML));
      const cartProductQuantity = Number(event.target.value);
      cartProductItemElement.querySelector(".cart-product-quantity-choice-result .text").innerHTML = `${utils.setPriceFormat(cartProductPrice * cartProductQuantity)}원`;

      page.setCartAmount();
    }
  });

  document.querySelector(".cart-product-item-list")?.addEventListener("click", function (event) {
    if (event.target.classList.contains("cart-product-quantity-delete")) {
      event.target.closest(".cart-product-item").remove();

      page.setCartAmount();
    }
  });

  document.querySelector(".cart-product-order-action .button")?.addEventListener("click", function (event) {
    page.createOrder().then((orderId) => page.createPayment(orderId, page.getCartAmount()));
  });
});

const page = {

  createOrder: async function () {
    const cartProducts = document.querySelectorAll(".cart-product-item");
    const orderProducts = [...cartProducts].map((cartProduct) => {
      const cartProductId = cartProduct.getAttribute("data-product-id");
      const cartProductQuantity = cartProduct.querySelector(".cart-product-quantity").value;
      return {
        id: cartProductId,
        quantity: cartProductQuantity,
      };
    });

    const { code, message, result } = await app.usePostByOrderCreate(orderProducts);

    return result.id;
  },

  createPayment: async function (orderId, orderAmount) {
    const { code, message, result } = await app.usePostByPaymentCreate(orderId);

    const paymentWidget = TossPayments("test_ck_26DlbXAaV0Le0KdQMa6KVqY50Q9R");
    paymentWidget.requestPayment({
      amount: orderAmount,
      orderId: orderId,
      orderName: "상품 결제",
    })
    .then(function (data) {
      console.log(data);
      console.log(data.paymentKey);
      app.usePostByPaymentComplete(orderId, data.paymentKey, orderAmount);
    })
    .catch(function (error) {
      app.usePostByPaymentCancel(orderId, "결제 실패");
    });
  },

  setCartAmount: function () {
    const cartAmountElement = document.querySelector(".cart-product-order-action .text");
    let cartAmount = 0;

    document.querySelectorAll(".cart-product-quantity-choice-result .text").forEach((element) => {
      cartAmount += Number(utils.unsetPriceFormat(element.innerHTML));
    });

    cartAmountElement.innerHTML = `${utils.setPriceFormat(cartAmount)}원 결제하기`;
  },

  getCartAmount: function () {
    const cartAmount = document.querySelector(".cart-product-order-action .text").innerHTML;

    return Number(utils.unsetPriceFormat(cartAmount));
  },

  setCartProduct: async function (productId) {
    if (!productId) {
      return;
    }

    const { code, message, result } = await app.useGetByProduct(productId);
    const cartProductItemListElement = document.querySelector(".cart-product-item-list");
    const product = result;

    cartProductItemListElement.insertAdjacentHTML("afterbegin", `
      <li class="cart-product-item" data-product-id=${product.id}>
        <div class="cart-product-info">
          <span class="text cart-product-name">${product.name}</span>
          <span class="text cart-product-price">${utils.setPriceFormat(product.price)}원</span>
        </div>
        <div class="cart-product-quantity-info">
          <div class="cart-product-quantity-choice">
            <input class="input cart-product-quantity" type="text" value="1"/>
            <button class="button cart-product-quantity-delete">
              <span class="text">&times;</span>
            </button>
          </div>
          <div class="cart-product-quantity-choice-result">
            <span class="text">${utils.setPriceFormat(product.price)}원</span>
          </div>
        </div>
      </li>
    `);
  },

  setCartProductList: async function () {
    const { code, message, result } = await app.useGetByProductList();
    const cartProductOrderSelectElement = document.querySelector(".cart-product-order .select");
    const products = result.products;

    products.forEach((product, i) => {
      cartProductOrderSelectElement.insertAdjacentHTML("beforeend", `<option value="${product.id}">${product.name} / ${utils.setPriceFormat(product.price)}원</option>`);
    });
  },

  /**
   * 주문 목록 페이지 이동
   */
  onRouteCartPage: function () {
    location.href("/orders");
  },
};