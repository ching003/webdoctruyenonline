const searchBox = document.getElementById("search-box");
const suggestions = document.getElementById("suggestions");

searchBox.addEventListener("input", function() {
    const query = searchBox.value;
    if (query.length > 0) {
        fetch(`/search?query=${query}`)
            .then(response => response.json())
            .then(data => {
                suggestions.innerHTML = ""; // Xóa gợi ý cũ
                data.forEach(story => {
                    const suggestionItem = document.createElement("li");
                    const link = document.createElement("a");
                    
                    link.textContent = story.title;
                    link.href = `/story-info/${story.id}`; 
                    link.style.textDecoration = "none";
                    
                    suggestionItem.appendChild(link);
                    suggestions.appendChild(suggestionItem);
                });
            });
    } else {
        suggestions.innerHTML = ""; // Xóa gợi ý nếu không có từ khóa
    }
});

// Ẩn gợi ý khi click ngoài
document.addEventListener("click", function(event) {
    if (event.target !== searchBox) {
        suggestions.innerHTML = "";
    }
});
