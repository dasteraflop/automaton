// Listen for messages from the background script
chrome.runtime.onMessage.addListener((request, sender, sendResponse) => {
  if (request.type === "sendSelectionRequest") {
    const selection = window.getSelection().toString();
    
    // Send the selected text back to the background script
    chrome.runtime.sendMessage({
      type: "selectionResult",
      data: selection
    });
  }
});

