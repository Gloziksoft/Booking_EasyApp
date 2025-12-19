console.log("✅ Skript načítaný");

document.addEventListener("DOMContentLoaded", function () {
  const form = document.querySelector("form");
  if (!form) return;

  const startInput = form.querySelector("[name='startDateTime']");
  const endInput = form.querySelector("[name='endDateTime']");
  const startError = document.getElementById("startError");
  const endError = document.getElementById("endError");

  form.addEventListener("submit", function (event) {
    startError.textContent = "";
    endError.textContent = "";

    const start = new Date(startInput.value);
    const end = new Date(endInput.value);

    // 🔹 Začiatok musí byť pred koncom
    if (start >= end) {
      event.preventDefault();
      endError.textContent = "Koniec rezervácie musí byť po začiatku.";
      endInput.focus();
      return;
    }

    // 🔹 Začiatok nesmie byť v minulosti
    const now = new Date();
    if (start < now) {
      event.preventDefault();
      startError.textContent = "Začiatok rezervácie nemôže byť v minulosti.";
      startInput.focus();
    }
  });
});
