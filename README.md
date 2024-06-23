# SWT Spring Boot Example

Questo progetto dimostra l'integrazione di SWT (Standard Widget Toolkit) con un'applicazione Spring Boot per mostrare popup utilizzando thread virtuali e altre tecniche di sincronizzazione.

## Struttura del Progetto

Il progetto è composto da diverse classi principali che gestiscono la logica per mostrare popup asincroni, sincronizzati e utilizzando thread virtuali. Di seguito è una panoramica delle classi principali:

- `SwtSpringBootApplication`: Classe principale che avvia l'applicazione Spring Boot e definisce diversi metodi di esecuzione per mostrare popup.
- `IShowPopup`: Interfaccia che definisce i metodi per mostrare popup.
- `PopupManager`: Implementazione di `IShowPopup` che gestisce la logica di visualizzazione dei popup.
- `LockUtils`: Classe di utilità per gestire la sincronizzazione dei thread utilizzando `ReentrantLock` e `Condition`.

## Esecuzione

L'applicazione esegue diverse dimostrazioni quando viene avviata:

1. **Prima Esecuzione (run1)**: Mostra un popup asincrono utilizzando `CountDownLatch` per bloccare l'esecuzione fino a quando il popup non viene chiuso, seguito da un popup sincrono.
2. **Seconda Esecuzione (run2)**: Mostra un popup utilizzando thread virtuali e blocca l'esecuzione utilizzando `ReentrantLock` fino a quando il popup non viene chiuso, seguito da un popup sincrono.
3. **Terza Esecuzione (run3)**: Mostra un popup asincrono senza bloccare l'esecuzione, seguito da un popup sincrono.
