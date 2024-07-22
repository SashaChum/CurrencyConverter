> **Примечание: Основныая ветка - это develop. Весь актуальный код в ней**.

\
&nbsp; Проект представляет собой приложение по работе с API [курса валют](https://www.exchangerate-api.com/),
состоящее из двух экранов:
1. Основной экран с двумя выпадающими списками (выбор базовой и целевой валют), полем для ввода суммы
и кнопкой "Рассчитать".
2. Информация о результате расчёта.

&nbsp; На первом экране необходимо выбрать базовую и целевую валюты, а также указать сумму.
Выпадающие списки содержат все актуальные коды валют и их полные наименования, получаемые из API
на момент запуска приложения.
&nbsp; Выпадающие списки поддерживают полнотекстовый поиск, а также скролл списка и тап по
нужному элементу.

&nbsp; При нажатии на кнопку "Рассчитать" происходит переход на второй экран с отображением
результата расчёта и указанием: из какой валюты в какую была произведена конвертация.
Информация по текущему курсу выбранных валют также загружается из API.

&nbsp; Помимо основного контента экраны обрабатывают состояния загрузки и ошибки.

&nbsp; Основные используемые технологии: viewBinding, Fragments, Retrofit, Coroutine Flow,
Jetpack Navigation, Dagger.