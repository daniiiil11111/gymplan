document.addEventListener('DOMContentLoaded', () => {

    // 1. Form page: disable the button and show a loading text after submit.
    const form = document.getElementById('plan-form');
    const submitBtn = document.getElementById('submit-btn');
    if (form && submitBtn) {
        const originalText = submitBtn.textContent;
        form.addEventListener('submit', () => {
            submitBtn.disabled = true;
            submitBtn.textContent = 'Creating plan...';
        });
        // If the user comes back with the browser's back button, the page may be restored as it was.
        window.addEventListener('pageshow', (event) => {
            if (event.persisted) {
                submitBtn.disabled = false;
                submitBtn.textContent = originalText;
            }
        });
    }

    // 2. Result page: copy the plan as plain text (table cells come out tab-separated).
    //    The button texts come from the page (data attributes), so they follow the plan language.
    const copyBtn = document.getElementById('copy-btn');
    const content = document.getElementById('plan-content');
    if (copyBtn && content) {
        const label = copyBtn.textContent;
        copyBtn.addEventListener('click', async () => {
            try {
                await navigator.clipboard.writeText(content.innerText);
                copyBtn.textContent = copyBtn.dataset.copied || 'Copied';
            } catch (error) {
                copyBtn.textContent = copyBtn.dataset.failed || 'Copy failed';
            }
            setTimeout(() => { copyBtn.textContent = label; }, 2000);
        });
    }

    // 3. Result page: print or save as PDF through the browser's print dialog.
    const printBtn = document.getElementById('print-btn');
    if (printBtn) {
        printBtn.addEventListener('click', () => window.print());
    }
});
