// --- カード生成関数 ---
function createCard(craft) {
	const card = document.createElement('section');
	card.className = 'card';

	let allDiffValue = craft.value.reduce((v1, v2) => v1 + v2, 0)
	let subTitle = document.createElement("div");
	subTitle.className = "sub-title";
	subTitle.textContent = craft.name + " " + allDiffValue;

	let characteristic;
	let isCycled = false;
	if (craft.characteristic === "木目変化") characteristic = "木目変化まであと"
	else if (craft.characteristic === "燃え木") characteristic = "木が燃えるまであと"
	else if (craft.characteristic === "再生木") characteristic = "木が再生するまであと"
	else if (craft.characteristic === "再生布") characteristic = "布が再生するまであと"
	else if (craft.characteristic === "虹布") {
		characteristic = "集中力半分まであと"
	}
	else characteristic = ""
	
	let characteristicTurn = 4;
	const characteristicTurnDiv = document.createElement("div");
	characteristicTurnDiv.className = "characteristic-turn";
	characteristicTurnDiv.textContent = characteristic + characteristicTurn + "ターン";
	if (characteristic === "") characteristicTurnDiv.textContent = ""

	const top = document.createElement("div");
	top.className = "top";
	top.appendChild(subTitle);
	if (characteristic != "") top.appendChild(characteristicTurnDiv);
	card.appendChild(top);

	// レイアウトクラスを選択
	let layoutClass = 'layout-row';
	if (craft.category === 'スティック') layoutClass = 'layout-stick';
	else if (craft.category === '扇') layoutClass = 'layout-fan';
	else if (craft.category === "両手杖") layoutClass = "layout-cane";
	else if (craft.category === "棍") layoutClass = "layout-rod";
	else if (craft.category === "弓") layoutClass = "layout-bow";
	else if (craft.category === "釣りざお") layoutClass = "layout-pole";
	else if (craft.category === "アタマ") layoutClass = "layout-head";
	else if (craft.category === "からだ上") layoutClass = "layout-body-u";
	else if (craft.category === "からだ下") layoutClass = "layout-body-b";
	else if (craft.category === "ウデ") layoutClass = "layout-arm";
	else if (craft.category === "足") layoutClass = "layout-foot";
	

	const container = document.createElement('div');
	container.className = layoutClass;

	// 小カード単位でリセット関数を格納
	const smallCardResets = [];

	// valueごとに小カードを作成
	(craft.value || []).forEach((v, i) => {
		const s = document.createElement('div');
		s.className = 'small-card';
		const originalValue = v;
		let currentValue = 0;
		let diffValue = v;

		const valueNameArea = document.createElement("div");
		valueNameArea.className = "value-name-area";

		const currentValueName = document.createElement("div");
		currentValueName.className = "current-value-name";
		currentValueName.textContent = "現在値";

		const diffValueName = document.createElement("div");
		diffValueName.className = "diff-value-name";
		diffValueName.textContent = "理論値までの値"

		const originalValueName = document.createElement("div");
		originalValueName.className = "original-value-name";
		originalValueName.textContent = "理論値";

		const valueArea = document.createElement("div");
		valueArea.className = "value-area";

		const currentValueDiv = document.createElement("div");
		currentValueDiv.className = "left-value";
		currentValueDiv.textContent = currentValue;

		const originalValueDiv = document.createElement("div");
		originalValueDiv.className = "right-value";
		originalValueDiv.textContent = originalValue;

		const diffValueDiv = document.createElement("div");
		diffValueDiv.className = "center-value";
		diffValueDiv.textContent = diffValue;

		const MinusInput = document.createElement('input');
		MinusInput.type = 'number';
		MinusInput.className = 'minus-input';
		MinusInput.dataset.craftName = craft.name;
		MinusInput.dataset.index = i;
		MinusInput.addEventListener("keydown", e => {
			if (e.key === "Enter") {
				e.preventDefault();
				const plusValue = Number(MinusInput.value || 0);
				currentValue = currentValue + plusValue;
				currentValueDiv.textContent = currentValue;
				diffValue = diffValue - plusValue;
				diffValueDiv.textContent = diffValue;
				allDiffValue = allDiffValue - plusValue;
				subTitle.textContent = craft.name + " " + allDiffValue;
				characteristicTurn = characteristicTurn - 1;
				if (characteristicTurn == 0) {
					characteristicTurn = 4;
					characteristicTurnDiv.classList.remove("lastTurn");
					if (characteristic === "虹布"){
						if(isCycled == false){
							characteristic = "集中力会心率UPまであと";
							isCycled = true;
						}
						else if(isCycled == true) {
							characteristic = "集中力半分まであと";
							isCycled = false;
						}
					}
				}
				else if (characteristicTurn == 1) {
					characteristicTurnDiv.classList.add("lastTurn");
				}
				characteristicTurnDiv.textContent = characteristic + characteristicTurn + "ターン";
				MinusInput.value = "";
			}
		});

		// --- 小カード単位のリセット関数 ---
		function resetSmallCard() {
			allDiffValue += currentValue;
			currentValue = 0;
			diffValue = originalValue;
			currentValueDiv.textContent = currentValue;
			diffValueDiv.textContent = diffValue;
			subTitle.textContent = craft.name + " " + allDiffValue;
			characteristicTurn = 4;
			characteristicTurnDiv.classList.remove("lastTurn");
			characteristicTurnDiv.textContent = characteristic + characteristicTurn + "ターン";
			MinusInput.value = "";
		}

		// リセット関数を格納
		smallCardResets.push(resetSmallCard);
		valueNameArea.appendChild(currentValueName);
		valueNameArea.appendChild(diffValueName);
		valueNameArea.appendChild(originalValueName);
		valueArea.appendChild(currentValueDiv);
		valueArea.appendChild(diffValueDiv);
		valueArea.appendChild(originalValueDiv);
		s.appendChild(valueNameArea);
		s.appendChild(valueArea);
		s.appendChild(MinusInput);
		container.appendChild(s);
	});
	const center = document.createElement('div');
	center.className = 'center';
	center.appendChild(container);
	card.appendChild(center);

	// --- カード単位リセットボタン ---
	const resetCardBtn = document.createElement("button");
	resetCardBtn.className = "card-reset-btn";
	resetCardBtn.textContent = "全リセット";
	resetCardBtn.addEventListener("click", () => {
		smallCardResets.forEach(fn => fn());
	});
	card.appendChild(resetCardBtn);

	return card;
}


// --- データ取得＆レンダリング ---
function loadCrafts() {
	console.log('loadCrafts実行');
	const cardList = document.getElementById('cardList');
	cardList.innerHTML = '読み込み中...';

	fetch(`${CONTEXT_PATH}api/craft/get`)
		.then(res => res.json())
		.then(data => {
			const crafts = Array.isArray(data) ? data : [data]; // 単体なら配列化

			cardList.innerHTML = ''; // 一旦クリア

			crafts.forEach(craft => {
				const card = createCard(craft);
				cardList.appendChild(card);
			});
		})
		.catch(err => {
			cardList.textContent = 'データ取得に失敗しました。';
			console.error(err);
		});
}

window.addEventListener("DOMContentLoaded", () => {
	loadCrafts();
});