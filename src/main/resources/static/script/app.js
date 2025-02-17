const app = {

  /**
   * 사용자 로그인
   */
  usePostByUserLogin: async function (
    userEmail,
    userPassword
  ) {
    try {
      const response = await axios.post(`http://localhost:8000/api/users/authenticate`, {
        email: userEmail,
        password: userPassword
      });
      return app.getPayload(response);
    } catch (error) {
      return app.getPayloadError(error);
    }
  },

  /**
   * 사용자 로그아웃
   */
  usePostByUserLogout: async function (
    // ...
  ) {
    // ...
  },

  /**
   * 상품 조회
   */
  useGetByProduct: async function (
    productId
  ) {
    try {
      const response = await axios.get(`http://localhost:8000/api/products/${productId}`);
      return app.getPayload(response);
    } catch (error) {
      return app.getPayloadError(error);
    }
  },

  /**
   * 상품 목록 조회
   */
  useGetByProductList: async function (
    // ...
  ) {
    try {
      const response = await axios.get(`http://localhost:8000/api/products`);
      return app.getPayload(response);
    } catch (error) {
      return app.getPayloadError(error);
    }
  },

  /**
   * 주문 생성
   */
  usePostByOrderCreate: async function (
    products
  ) {
    try {
      const requestBody = {
        products: products,
      }
      const response = await axios.post(`http://localhost:8000/api/orders`, requestBody);
      return app.getPayload(response);
    } catch (error) {
      return app.getPayloadError(error);
    }
  },

  /**
   * 주문 조회
   */
  useGetByOrder: async function (
    orderId
  ) {
    try {
      const response = await axios.get(`http://localhost:8000/api/orders/${orderId}`);
      return app.getPayload(response);
    } catch (error) {
      return app.getPayloadError(error);
    }
  },

  /**
   * 주문 목록 조회
   */
  useGetByOrderList: async function (
    // ...
  ) {
    try {
      const response = await axios.get(`http://localhost:8000/api/orders`);
      return app.getPayload(response);
    } catch (error) {
      return app.getPayloadError(error);
    }
  },

  /**
   * 결제 조회
   */
  useGetByPayment: async function (
    orderId
  ) {
    try {
      const response = await axios.get(`http://localhost:8000/api/orders/${orderId}/payments`);
      return app.getPayload(response);
    } catch (error) {
      return app.getPayloadError(error);
    }
  },

  /**
   * 결제 생성
   */
  usePostByPaymentCreate: async function (
    orderId
  ) {
    try {
      const requestBody = {
        orderId: orderId
      }
      const response = await axios.post(`http://localhost:8000/api/payments`, requestBody);
      return app.getPayload(response);
    } catch (error) {
      return app.getPayloadError(error);
    }
  },

  /**
   * 결제 승인
   */
  usePostByPaymentComplete: async function (
    orderId,
    transactionKey,
    transactionAmount
  ) {
    try {
      const requestBody = {
        orderId: orderId,
        transactionKey: transactionKey,
        transactionAmount: transactionAmount
      }
      const response = await axios.post(`http://localhost:8000/api/payments/complete`, requestBody);
      return app.getPayload(response);
    } catch (error) {
      return app.getPayloadError(error);
    }
  },

  /**
   * 결제 취소
   */
  usePostByPaymentCancel: async function (
    orderId,
    transactionReason
  ) {
    try {
      const requestBody = {
        orderId: orderId,
        transactionReason: transactionReason,
      }
      const response = await axios.post(`http://localhost:8000/api/payments/cancel`, requestBody);
      return app.getPayload(response);
    } catch (error) {
      return app.getPayloadError(error);
    }
  },

  /**
   * API 응답을 처리한다.
   */
  getPayload: function (
    response
  ) {
    if (response === undefined) {
      return app.toObject({ code: "-100", message: "서버 오류", result: null});
    } else {
      return app.toObject(response.data);
    }
  },

  /**
   * API 에러 응답을 처리한다.
   */
  getPayloadError: function (
    error
  ) {
    if (error === undefined) {
      return app.toObject({ code: "-100", message: "서버 오류", result: null});
    } else {
      return app.toObject(error.data);
    }
  },

  /**
   * API 응답 데이터를 처리한다.
   */
  toObject: function (
    data
  ) {
    console.log(data);

    return {
      code: data.code,
      message: data.message,
      result: data.result,
    };
  },
};