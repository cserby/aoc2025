# IntelliJ IDEA AI Configuration

This repository is an **AI-free coding zone**. See [../AGENTS.md](../AGENTS.md) for the complete policy.

## Automatic Configuration

The `.idea` directory contains configuration files that should automatically disable AI features. However, you should verify these settings.

## Manual Steps to Disable AI in IntelliJ IDEA

### 1. Disable JetBrains AI Assistant

**Important:** JetBrains AI Assistant may use multiple AI models as backends including:
- Claude (Anthropic)
- GPT-4 (OpenAI)
- Gemini (Google)
- Other models

Disabling it prevents all AI assistance regardless of the underlying model.

1. Go to **Settings/Preferences** → **Tools** → **AI Assistant**
2. Uncheck **Enable AI Assistant**
3. Uncheck **Show inline code completion**
4. Uncheck **Enable AI chat**
5. Click **Apply**

If you see a "Sign out" option, use it to disconnect from JetBrains AI services completely.

### 2. Disable ML-Powered Code Completion

1. Go to **Settings/Preferences** → **Editor** → **General** → **Code Completion**
2. Uncheck **Enable ML-powered code completion**
3. Uncheck **Sort suggestions by machine learning**
4. Click **Apply**

### 3. Disable/Uninstall AI Plugins

Go to **Settings/Preferences** → **Plugins** and disable/uninstall:

- ❌ **GitHub Copilot** (com.github.copilot)
- ❌ **Tabnine** (com.tabnine.TabNine)
- ❌ **Codeium** (com.codeium.intellij)
- ❌ **AWS Toolkit** (for CodeWhisperer) (aws.toolkit)
- ❌ **Claude** / **Anthropic Claude** (com.anthropic.claude, com.anthropic.claude-dev, claude-code)
- ❌ **Google Cloud Code** / **Gemini** (com.google.cloudcode, com.google.gct.core, com.google.cloud.tools.intellij, com.google.generative-ai-studio)
- ❌ **JetBrains AI Assistant** (if installed as plugin)
- ❌ Any other AI code completion or chat plugin

### 4. Verify Full Line Completion is Disabled

1. Go to **Settings/Preferences** → **Editor** → **General** → **Code Completion**
2. Ensure **Full line code completion** is unchecked
3. Click **Apply**

### 5. Disable Claude/Anthropic Plugin (if installed)

1. Go to **Settings/Preferences** → **Tools** → **Claude** (if present)
2. Uncheck all Claude-related options
3. Disable code completions, inline suggestions, and chat
4. Click **Apply**

Or better yet: **Uninstall the Claude plugin completely** from the Plugins section.

### 6. Disable Google Gemini / Cloud Code (if installed)

1. Go to **Settings/Preferences** → **Tools** → **Cloud Code** (if present)
2. Uncheck **Enable Gemini Code Assist**
3. Uncheck **Enable AI-powered code completion**
4. Uncheck **Enable code generation**
5. Click **Apply**

Or better yet: **Uninstall the Cloud Code plugin completely** from the Plugins section.

**Note:** Google Cloud Code includes Gemini Code Assist (powered by Gemini AI). Disabling Cloud Code prevents all Gemini-based assistance.

### 7. Disable Cloud-Based Features

1. Go to **Settings/Preferences** → **Tools** → **Machine Learning**
2. Uncheck **Send usage statistics to JetBrains**
3. Uncheck **Enable cloud-based machine learning**
4. Click **Apply**

## Verification

After applying these settings:
1. Restart IntelliJ IDEA
2. Open a TypeScript file
3. Start typing - you should see NO AI-powered suggestions
4. Verify the AI Assistant icon is not visible in the toolbar

## Why?

This is an Advent of Code boilerplate. The purpose is **learning through manual problem-solving**, not having AI write solutions.

If you want AI assistance, use a different repository.

---

For questions about this policy, see [../AGENTS.md](../AGENTS.md)
