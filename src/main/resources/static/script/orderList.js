document.addEventListener("DOMContentLoaded", function (e) {
  page.setOrderList().then();
});

const page = {

  /**
   * 주문 목록
   */
  setOrderList: async function () {
    const orderTableHeadElement = document.querySelector(".order-table .table-head");
    const { code, message, result } = await app.useGetByOrderList();
    
    if (code !== "SUCCESS") {
      // alert("주문 내역을 찾을 수 없습니다.");
      return;
    }

    if (code === "SUCCESS" && result.orders.length === 0) {
      // alert("주문 내역이 없습니다.");
      return;
    }

    result.orders.forEach((order, i) => {
      if (order.orderProducts.length === 1) {
        orderTableHeadElement.insertAdjacentHTML("afterend", `
          <tbody class="table-body">
            <tr class="table-row">
              <td class="table-data" rowspan="1">
                <div class="order-info">
                  <span class="order-id">${order.id}</span>
                  <span class="order-status">${order.status}</span>
                </div>
              </td>
              <td class="table-data" rowspan="1">
                <div class="order-product-info">
                  <span class="order-product-name">${order.orderProducts[0].name}</span>
                  <span class="order-product-quantity">수량: ${order.orderProducts[0].quantity}</span>
                </div>
              </td>
              <td class="table-data" rowspan="1">
                <div class="order-product-price-info">
                  <span class="order-product-price">${utils.setPriceFormat(order.orderProducts[0].price)}원</span>
                </div>
              </td>
              <td class="table-data" rowspan="1">
                <div class="order-payment-info">
                  <span class="order-payment-amount">${utils.setPriceFormat(order.amount)}원</span>
                  <a class="link" href=${`/order/${order.id}`}>[더보기]</a>
                </div>
              </td>
            </tr>
          </tbody>
        `);
      } else {
        orderTableHeadElement.insertAdjacentHTML("afterend", `
          <tbody class="table-body">
            <tr class="table-row">
              <td class="table-data" rowspan=${order.orderProducts.length}>
                <div class="order-info">
                  <span class="order-id">${order.id}</span>
                  <span class="order-status">${order.status}</span>
                </div>
              </td>
              <td class="table-data" rowspan="1">
                <div class="order-product-info">
                  <span class="order-product-name">${order.orderProducts[0].name}</span>
                  <span class="order-product-quantity">수량: ${order.orderProducts[0].quantity}</span>
                </div>
              </td>
              <td class="table-data" rowspan="1">
                <div class="order-product-price-info">
                  <span class="order-product-price">${utils.setPriceFormat(order.orderProducts[0].price)}원</span>
                </div>
              </td>
              <td class="table-data" rowspan=${order.orderProducts.length}>
                <div class="order-payment-info">
                  <span class="order-payment-amount">${utils.setPriceFormat(order.amount)}원</span>
                  <a class="link" href=${`/order/${order.id}`}>[더보기]</a>
                </div>
              </td>
            </tr>
            ${order.orderProducts.map((orderProduct, i) => {
              if (i > 0) {
                return `
                  <tr class="table-row">
                    <td class="table-data" rowspan="1">
                      <div class="order-product-info">
                        <span class="order-product-name">${orderProduct.name}</span>
                        <span class="order-product-quantity">수량: ${orderProduct.quantity}</span>
                      </div>
                    </td>
                    <td class="table-data" rowspan="1">
                      <div class="order-product-price-info">
                        <span class="order-product-price">${utils.setPriceFormat(orderProduct.price)}원</span>
                      </div>
                    </td>
                  </tr>
                `;
              };
            }).join("")}
          </tbody>
        `);
      }
    });
  },

  /**
   * 주문 목록 페이지 이동
   */
  onRouteCartPage: function () {
    location.href("/orders");
  },
};