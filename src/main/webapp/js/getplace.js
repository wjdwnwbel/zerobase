
function success({ coords, timestamp }) {
	const latitude = coords.latitude;   // 위도
	const longitude = coords.longitude; // 경도
	
	document.getElementById('lnt').value = longitude;
	document.getElementById('lat').value = latitude;

}

function getUserLocation() {
	if (!navigator.geolocation) {
		throw "위치 정보가 지원되지 않습니다.";
	}
	navigator.geolocation.getCurrentPosition(success);
}

