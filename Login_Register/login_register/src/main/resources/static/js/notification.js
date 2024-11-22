function loadNotifications() {
    const panel = document.getElementById('notification-panel');
    const list = document.getElementById('notification-list');

    panel.style.display = panel.style.display === 'block' ? 'none' : 'block';
    if (panel.style.display === 'block' && !list.hasAttribute('data-loaded')) {
        fetch('/api/notifications/list')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to load notifications');
                }
                return response.json();
            })
            .then(notifications => {
                // Xóa nội dung cũ
                list.innerHTML = '';

                if (notifications && notifications.length > 0) {
                    notifications.forEach(notification => {
                        if (notification.chapterId && notification.message) {
                            //hợp lệ
                            const item = document.createElement('li');
                            const link = document.createElement('a');
                            link.href = `/chapter/${notification.chapterId}`;
                            link.textContent = notification.message;
                            item.appendChild(link);
                            list.appendChild(item);
                        }
                    });

                    //không có thông báo
                    if (list.innerHTML === '') {
                        list.innerHTML = '<li>Không có thông báo nào</li>';
                    }

                } else {
                    list.innerHTML = '<li>Không có thông báo nào</li>';
                }
                list.setAttribute('data-loaded', 'true');
            })
            .catch(error => {
                console.error('Error:', error);
                list.innerHTML = '<li>Không thể tải thông báo</li>';
            });
    }
}