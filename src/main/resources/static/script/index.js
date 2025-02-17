document.addEventListener("DOMContentLoaded", function (e) {

  /**
   * 로그인 이벤트 등록
   */
  document.querySelector(".user-login-button")?.addEventListener("click", function (event) {
    page.onLogin().then(() => page.onRouteCartPage());
  });
});

const page = {

  /**
   * 사용자 로그인 처리
   */
  onLogin: async function () {
    const userEmailInput = document.querySelector(".user-email-input");
    const userPasswordInput = document.querySelector(".user-password-input");
    const { code, message } = await app.usePostByUserLogin(userEmailInput.value, userPasswordInput.value);

    if (code !== "SUCCESS") {
      alert(message);
      return;
    }
  },

  /**
   * 장바구니 페이지 이동
   */
  onRouteCartPage: function () {
    location.replace("/cart");
  },
};