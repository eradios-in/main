document.addEventListener('DOMContentLoaded', pageSetup);

function pageSetup() {
    closePageLoader();
    disableRightClickAndInspect();
}

function closePageLoader() {
    // disable pageloader
    setTimeout(function () {
        const loader = document.getElementById('pageLoader');
        if (loader) {
            loader.style.opacity = '0';
            setTimeout(() => loader.style.display = 'none', 400);
        }
    }, 400);
}
function disableRightClickAndInspect() {
    document.addEventListener('contextmenu', function (e) {
        e.preventDefault();
    });
    document.addEventListener('keydown', function (e) {
        // F12
        if (e.keyCode === 123) {
            e.preventDefault();
        }
        // Ctrl+Shift+I
        if (e.ctrlKey && e.shiftKey && e.keyCode === 73) {
            e.preventDefault();
        }
        // Ctrl+Shift+J (console)
        if (e.ctrlKey && e.shiftKey && e.keyCode === 74) {
            e.preventDefault();
        }
        // Ctrl+U (view source)
        if (e.ctrlKey && e.keyCode === 85) {
            e.preventDefault();
        }
        // Ctrl+Shift+C (inspect element)
        if (e.ctrlKey && e.shiftKey && e.keyCode === 67) {
            e.preventDefault();
        }
    });
}
