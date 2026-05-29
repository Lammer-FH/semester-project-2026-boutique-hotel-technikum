/* eslint-disable no-console */
type LogLevel = "info" | "warn" | "error"

interface LogEntry {
  level: LogLevel
  message: string
  context: string
  timestamp: string
  data?: Record<string, unknown>
}

const formatEntry = (entry: LogEntry): string =>
  `[${entry.timestamp}] [${entry.level.toUpperCase()}] [${entry.context}] ${entry.message}`

const emit = (entry: LogEntry) => {
  const formatted = formatEntry(entry)

  switch (entry.level) {
    case "error":
      console.error(formatted, entry.data ?? "")
      break
    case "warn":
      console.warn(formatted, entry.data ?? "")
      break
    default:
      console.log(formatted, entry.data ?? "")
  }
}

export const logger = {
  info(context: string, message: string, data?: Record<string, unknown>) {
    emit({
      level: "info",
      message,
      context,
      timestamp: new Date().toISOString(),
      data,
    })
  },

  warn(context: string, message: string, data?: Record<string, unknown>) {
    emit({
      level: "warn",
      message,
      context,
      timestamp: new Date().toISOString(),
      data,
    })
  },

  error(context: string, message: string, data?: Record<string, unknown>) {
    emit({
      level: "error",
      message,
      context,
      timestamp: new Date().toISOString(),
      data,
    })
  },
}
