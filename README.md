環境與執行過程:
1.執行環境為Linux，安裝java 1.8.0或以上
2.開啟terminal按以下步驟執行
	a: ./compile1.sh
	b: ./compile2.sh
	c: ./run1.sh
	d: ./run2.sh
簡單說明:
compile1.sh以及compile2.sh為為了編譯所寫的shell script。使用到java的hamcrest(斷言測試的匹配工具:assertion matchers)和junit(單元測試工具:junit)，以上皆包含在lib資料夾當中。
1.執行./run1.sh可以產生test_results.txt，包含對資料庫的分析結果(總人數、有分享及沒分享居住位置的人數、lower bound及upper bound)
2.執行./run2.sh可以透過2種演算法(baseline以及BFR/K-means)分別預測twitter未提供地址資訊之用戶的位置，並和groundtruth比較計算準確率。
3.若將location_analysis_test取消註解第31行及第38~41行，再執行./run2.sh則可以產生inference.html，其包含twitter用戶的位置分布分析結果，以及標示出演算法推測出的twitter用戶位置。
