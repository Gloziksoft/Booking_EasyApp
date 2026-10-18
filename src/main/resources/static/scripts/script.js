console.log("Skript načítaný");

document.addEventListener("DOMContentLoaded", function () {
    // Zobrazenie a skrytie hesla
    function setupPasswordToggle(buttonId, inputId) {
        const button = document.getElementById(buttonId);
        const input = document.getElementById(inputId);
        if (!button || !input) return;

        const icon = button.querySelector("i");
        if (!icon) return;

        button.addEventListener("click", function () {
            const show = input.type === "password";
            input.type = show ? "text" : "password";
            icon.classList.toggle("bi-eye", !show);
            icon.classList.toggle("bi-eye-slash", show);
        });
    }

    setupPasswordToggle("togglePassword", "password");
    setupPasswordToggle("toggleConfirmPassword", "confirmPassword");

    // Validácia rezervácie
    const form = document.querySelector("form");
    if (!form) return;

    const startInput = form.querySelector("[name='startDateTime']");
    const endInput = form.querySelector("[name='endDateTime']");
    const startError = document.getElementById("startError");
    const endError = document.getElementById("endError");

    if (!startInput || !endInput || !startError || !endError) return;

    form.addEventListener("submit", function (event) {
        startError.textContent = "";
        endError.textContent = "";

        const start = new Date(startInput.value);
        const end = new Date(endInput.value);

        if (start >= end) {
            event.preventDefault();
            endError.textContent = "Koniec rezervácie musí byť po začiatku.";
            endInput.focus();
            return;
        }

        const now = new Date();
        if (start < now) {
            event.preventDefault();
            startError.textContent = "Začiatok rezervácie nemôže byť v minulosti.";
            startInput.focus();
        }
    });
});