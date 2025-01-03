# Лабораторная работа №3

`Русакова Елена Дмитриевна`

ИСУ `367519`

группа `P3317`

## Требования

Цель: получить навыки работы с вводом/выводом, потоковой обработкой данных, командной строкой.

В рамках лабораторной работы вам предлагается повторно реализовать лабораторную работу по предмету "Вычислительная математика" посвящённую интерполяции (в разные годы это лабораторная работа 3 или 4) со следующими дополнениями:

- обязательно должна быть реализована линейная интерполяция (отрезками, [link](https://en.wikipedia.org/wiki/Linear_interpolation));
- настройки алгоритма интерполяции и выводимых данных должны задаваться через аргументы командной строки:
    - какие алгоритмы использовать (в том числе два сразу);
    - частота дискретизации результирующих данных;
    - и т.п.;
- входные данные должны задаваться в текстовом формате на подобии ".csv" (к примеру `x;y\n` или `x\ty\n`) и подаваться на стандартный ввод, входные данные должны быть отсортированы по возрастанию x;
- выходные данные должны подаваться на стандартный вывод;
- программа должна работать в потоковом режиме (пример -- `cat | grep 11`), это значит, что при запуске программы она должна ожидать получения данных на стандартный ввод, и, по мере получения достаточного количества данных, должна выводить рассчитанные точки в стандартный вывод;

Приложение должно быть организовано следующим образом:

```text
    +---------------------------+
    | обработка входного потока |
    +---------------------------+
            |
            | поток / список / последовательность точек
            v
    +-----------------------+      +------------------------------+
    | алгоритм интерполяции |<-----| генератор точек, для которых |
    +-----------------------+      | необходимо вычислить         |
            |                      | промежуточные значения       |
            |                      +------------------------------+
            |
            | поток / список / последовательность рассчитанных точек
            v
    +------------------------+
    | печать выходных данных |
    +------------------------+
```

Потоковый режим для алгоритмов, работающих с группой точек должен работать следующим образом:

```text
o o o o o o . . x x x
  x x x . . o . . x x x
    x x x . . o . . x x x
      x x x . . o . . x x x
        x x x . . o . . x x x
          x x x . . o . . x x x
            x x x . . o o o o o o EOF
```

где:

- каждая строка -- окно данных, на основании которых производится расчёт алгоритма;
- строки сменяются по мере поступления в систему новых данных (старые данные удаляются из окна, новые -- добавляются);
- `o` -- рассчитанные данные, можно видеть:
    - большинство окон используется для расчёта всего одной точки, так как именно в "центре" результат наиболее точен;
    - первое и последнее окно используются для расчёта большого количества точек, так лучших данных для расчёта у нас не будет.
- `.` -- точки, задействованные в рассчете значения `o`.
- `x` -- точки, расчёт которых для "окон" не требуется.

Пример вычислений для шага 1.0 и функции sin(x):

```text
Ввод первых двух точек (в данном примере X Y через пробел):
0 0.00
1.571 1

Вывод:
Линейная (идем от первой точки из введенных (0.00) с шагом 1, покрывая все введенные X (1.571 < 2)):
0.00    1.00    2.00
0.00    0.64    1.27

Ввод третьей точки:
3.142 0

Следующий вывод:
Линейная (идем от второй точки из введенных (1.571) с шагом 1, покрывая все введенные X (3.142 < 3.57)):
1.57    2.57    3.57
1.00    0.36    -0.27

Ввод четвертой точки:
4.712 -1

Следующий вывод:
Линейная (идем от третьей точки из введенных (3.142) с шагом 1, покрывая все введенные X (4.712 < 5.14)):
3.14    4.14    5.14
0.00    -0.64   -1.27

Лагранж (теперь количество введенных точек повзоляет его рассчитать, идем от первой точки (0.00) из введенных с шагом 1, покрывая все введенные X (4.712 < 5)):
0.00    1.00    2.00    3.00    4.00    5.00
0.00    0.97    0.84    0.12    -0.67   -1.03

Ввод пятой точки:
12.568  0

Следующий вывод:
Линейная (идем от четвертой точки из введенных (4.712) с шагом 1, покрывая все введенные X (12.568 < 12.71))):
4.71    5.71    6.71    7.71    8.71    9.71    10.71   11.71   12.71
-1.00   -0.87   -0.75   -0.62   -0.49   -0.36   -0.24   -0.11   0.02

Лагранж (идем от второй точки из введенных (1.571) с шагом 1, покрывая все введенные X (12.568 < 12.57))):
1.57    2.57    3.57    4.57    5.57    6.57    7.57    8.57    9.57    10.57   11.57   12.57
1.00    0.37    -0.28   -0.91   -1.49   -1.95   -2.26   -2.38   -2.25   -1.84   -1.11   0.00

И т.д.
```

Как видно из примера выше, окна для каждого метода двигаются по-разному. Для линейной окно начало сдвигаться уже при вводе третьей точки (т.к. для вычисления нужно всего две), в то время как для Лагранжа окно начало двигаться только когда была введена пятая точка (т.к. здесь для вычислений нужно больше точек).

Общие требования:

- программа должна быть реализована в функциональном стиле;
- ввод/вывод должен быть отделён от алгоритмов интерполяции;
- требуется использовать идиоматичный для технологии стиль программирования.


## Реализация

+ Линейная интерполяция 

```clojure
(defn linear [point1 point2 x]
  (let [a  (/ (- (:y point2) (:y point1)) (- (:x point2) (:x point1)))
        b  (- (:y point1) (* a (:x point1)))]
    (->Point x  (+ (* a x 1.0) b))))
```

+ Интерполяция методом Лагранжа

```clojure
(defn lagrange [points x]
  (let [y (reduce (fn [acc i]
                    (let [xi (:x (nth points i))
                          yi (:y (nth points i))
                          term (reduce (fn [prod j]
                                         (if (= i j)
                                           prod
                                           (* prod (/ (- x (:x (nth points j))) (- xi (:x (nth points j)))))))
                                       1
                                       (range 4))]
                      (+ acc (* yi term))))
                  0
                  (range 4))]
    (->Point x y)))
```

+ Генератор точек, для которых нужно вычислить значения

``` clojure
(defn count-generating-points [first-point last-point step]
  (Math/ceil (/ (+ (- (:x last-point) (:x first-point)) step) step)))

(defn generating-points [points step]
  (let [point-count (count-generating-points (first points) (last points) step)]
    (take point-count (iterate (fn [x] (+ x step)) (:x (first points))))))
```


+ Обработка входного потока точек

В случае ввода некорректных данных (пустой ввод, не числа, не верное количество чисел), выводится ошибка и программа завершается.

``` clojure
(defn parse-point [line]
  (if (str/blank? line)
    ((println "Empty input, exit.")
     (System/exit 0))
    (let [strs (str/split line #"[, ;]+")]
      (if (= (count strs) 2)
        (let [[str-x str-y] strs
              x (parse-double str-x)
              y (parse-double str-y)]
          (if (or (nil? x) (nil? y))
            ((println "Not number input, exit.")
             (System/exit 0))
            (->Point x y)))
        ((println "Bad input, exit.")
         (System/exit 0))))))

(defn reader []
  (let [p (line-seq (java.io.BufferedReader. *in*))
        p2 (map str/trim p)
        p3 (map parse-point p2)]
    p3))
```    

+ Чтение и валидация аргументов командной строки с помощью `clojure tools cli`

``` clojure 
(def cli-options
  [["-a" "--algorithm ALG"
    :parse-fn #(str/lower-case (str/trim %))
    :validate [#(or (= % "linear") (= % "lagrange") (= % "all")) "Wrong algorithm."]]

   ["-s" "--step STEP"
    :default 1.0
    :parse-fn #(parse-double %)
    :validate [#(pos? %) "Negative step, exit."]]

   ["-h" "--help"]])

(defn validate-args [args]
  (let [{:keys [options errors summary]} (parse-opts args cli-options)]
    (cond
      (:help options) (exit 0 (usage summary))
      errors (exit 1 (error-msg errors))
      (empty? (:algorithm options)) (exit 1 "No algorithm, exit.")
      :else {:options options})))
```

## Примеры работы программы 

+ Запуск программы без задания алгоритма

``` shell
> lein run
No algorithm, exit.
```

+ Запуск программы с выбором всех алгоритмов и шага интерполяции, и ввод не числового значения вместо значения точки.

``` shell
> lein run -a all -s 0.2
0 0
2 1.5
Linear:
0,00    0,20    0,40    0,60    0,80    1,00    1,20    1,40    1,60    1,80    2,00
0,00    0,15    0,30    0,45    0,60    0,75    0,90    1,05    1,20    1,35    1,50
3.2 4.3
Linear:
2,00    2,20    2,40    2,60    2,80    3,00    3,20
1,50    1,97    2,43    2,90    3,37    3,83    4,30
4.5 2
Linear:
3,20    3,40    3,60    3,80    4,00    4,20    4,40    4,60
4,30    3,95    3,59    3,24    2,88    2,53    2,18    1,82
Lagrange:
0,00    0,20    0,40    0,60    0,80    1,00    1,20    1,40    1,60    1,80    2,00    2,20    2,40    2,60    2,80    3,00    3,20    3,40    3,60    3,80   4,00     4,20    4,40    4,60
0,00    -0,54   -0,87   -1,00   -0,97   -0,79   -0,49   -0,08   0,40    0,93    1,50    2,08    2,64    3,17    3,63    4,02    4,30    4,45    4,46    4,29   3,92     3,34    2,51    1,42
7 num 
Not number input, exit.
```

+ Запуск программы с выбором метода Лагранжа, без выбора шага интерполяции

``` shell
> lein run -a lagrange
0 0.0    
1.571 1 
3.142 0 
4.712 -1
Lagrange:
0,00    1,00    2,00    3,00    4,00    5,00
0,00    0,97    0,84    0,12    -0,67   -1,03
12.568 0
Lagrange:
1,57    2,57    3,57    4,57    5,57    6,57    7,57    8,57    9,57    10,57   11,57   12,57
1,00    0,37    -0,28   -0,91   -1,49   -1,95   -2,26   -2,38   -2,25   -1,84   -1,11   0,00

Empty input, exit.
```

+ Запуск программы с неверным именем метода интерполяции

``` shell
> lein run -a test    
The following errors occurred while parsing your command:

Failed to validate "-a test": Wrong algorithm.
```

## Вывод
Во время выполнения лабораторной работы, я познакомилась с вводом и выводом, потоковой обработкой данных и использованием аргументов командой строки в функциональном языке программирования Clojure.

В этой лабораторной почти не получилось (кроме функций самой интерполяции) работать с repl'ом и это грустно. 

