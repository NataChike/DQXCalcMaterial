let currentSuggestions = [];
let itemsData = [];
let allItems = [];
let selectedItems = [];

const input = document.getElementById("itemInput");
const suggestionsContainer = document.getElementById("suggestions");
const jobTabs = document.getElementById("jobTabs");
const jobItemsDiv = document.getElementById("jobItems");
const itemsList = document.getElementById("itemsList");
const requiredMaterialList = document.getElementById("requiredMaterialList");
const calculateBtn = document.getElementById("calculateBtn");
const resetBtn = document.getElementById("resetBtn");
const craftBtn = document.getElementById("craftBtn");

// サジェスト表示
function showSuggestions(value) {
	suggestionsContainer.innerHTML = "";
	currentSuggestions = [];
	if (!value) return;

	const filtered = itemsData.filter(item => item.includes(value));
	currentSuggestions = filtered;

	filtered.forEach(item => {
		const div = document.createElement("button");
		div.type = "button";
		div.className = "list-group-item list-group-item-action";
		div.textContent = item;
		div.addEventListener("click", () => addItem(item));
		suggestionsContainer.appendChild(div);
	});
}

// 作成物追加
function addItem(itemName) {
	if (!itemName) return;

	selectedItems.push(itemName);

	const div = document.createElement("div");
	div.classList.add("item");
	div.textContent = itemName;

	const closeBtn = document.createElement("span");
	closeBtn.textContent = "✕";
	closeBtn.addEventListener("click", () => {
		itemsList.removeChild(div);
		const index = selectedItems.indexOf(itemName);
		if (index > -1) selectedItems.splice(index, 1);
	});

	div.appendChild(closeBtn);
	itemsList.appendChild(div);

	input.value = "";
	suggestionsContainer.innerHTML = "";
	currentSuggestions = [];
}

// 入力イベント
input.addEventListener("input", e => showSuggestions(e.target.value.trim()));
input.addEventListener("keydown", e => {
	if (e.key === "Enter") {
		e.preventDefault();
		if (currentSuggestions.length > 0) addItem(currentSuggestions[0]);
	}
});

// 職人タブ作成
function renderJobTabs(jobs) {
	jobTabs.innerHTML = "";
	jobs.forEach(job => {
		const tab = document.createElement("div");
		tab.classList.add("tab");
		tab.textContent = job;
		tab.addEventListener("click", () => {
			document.querySelectorAll(".tab").forEach(t => t.classList.remove("active"));
			tab.classList.add("active");
			renderJobItems(job);
		});
		jobTabs.appendChild(tab);
	});
}

// 職人作成物表示（カード）
function renderJobItems(job) {
	jobItemsDiv.innerHTML = "";
	const items = allItems.filter(item => item.job === job);

	items.forEach(item => {
		const col = document.createElement("div");
		col.className = "col";

		const card = document.createElement("div");
		card.className = "card job-card p-2 text-center";
		card.textContent = item.name;
		card.addEventListener("click", () => addItem(item.name));

		col.appendChild(card);
		jobItemsDiv.appendChild(col);
	});
}

// 計算ボタン
calculateBtn.addEventListener("click", () => {
	if (selectedItems.length === 0) {
		alert("作成物を選択してください。");
		return;
	}
	fetch(`${CONTEXT_PATH}api/calculate`, {
		method: "POST",
		headers: { "Content-Type": "application/json" },
		body: JSON.stringify({ items: selectedItems })
	})
		.then(res => res.json())
		.then(data => {
			requiredMaterialList.innerHTML = "";
			for (const genre in data) {
				const h3 = document.createElement("h3");
				h3.textContent = genre;
				requiredMaterialList.appendChild(h3);

				const ul = document.createElement("ul");
				data[genre].forEach(mat => {
					const li = document.createElement("li");
					li.textContent = `${mat.name} x ${mat.num}`;
					ul.appendChild(li);
				});
				requiredMaterialList.appendChild(ul);
			}
		})
		.catch(err => alert("送信に失敗しました。"));
});

// リセットボタン
resetBtn.addEventListener("click", () => {
	input.value = "";
	selectedItems = [];
	itemsList.innerHTML = "";
	suggestionsContainer.innerHTML = "";
	currentSuggestions = [];
	requiredMaterialList.innerHTML = "";
});

//クラフトボタン
craftBtn.addEventListener("click", () => {
	if (selectedItems.length === 0) {
		alert("作成物を選択してください。");
		return;
	}
	fetch(`${CONTEXT_PATH}api/craft`, {
		method: 'POST',
		headers: { 'Content-Type': 'application/json' },
		body: JSON.stringify({ items: selectedItems })
	}).then(() => {
		window.open(`${CONTEXT_PATH}craft`, '_blank');
	})
		.catch(error => console.error('送信エラー:', error));
})

// ページ読み込み時
window.addEventListener("DOMContentLoaded", () => {
	const fetchItemsData = fetch(`${CONTEXT_PATH}api/items/names`).then(res => res.json());
	const fetchAllItems = fetch(`${CONTEXT_PATH}api/items`).then(res => res.json());

	Promise.all([fetchItemsData, fetchAllItems])
		.then(([itemsDataResponse, allItemsResponse]) => {
			itemsData = itemsDataResponse;
			allItems = allItemsResponse;

			const jobs = [...new Set(allItems.map(item => item.job))];
			renderJobTabs(jobs);
		})
		.catch(err => console.error("データ取得失敗:", err));
});
